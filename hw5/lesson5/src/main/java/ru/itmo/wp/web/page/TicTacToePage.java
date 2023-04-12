package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private static final String STATE = "state";
    private static final String CELL_PREFIX = "cell_";

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute(STATE);
        if (Objects.isNull(state)) {
            state = new State();
            request.getSession().setAttribute(STATE, state);
        }
        view.put(STATE, state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute(STATE);
        if (Objects.isNull(state)) {
            state = new State();
            view.put(STATE, state);
            request.getSession().setAttribute(STATE, state);
        }
        if (state.over()) {
            view.put(STATE, state);
            throw new RedirectException("/ticTacToe");
        }
        for (Map.Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
            if (parameter.getKey().startsWith(CELL_PREFIX)) {
                System.out.println(parameter.getKey());
                String cellNumber = parameter.getKey().substring(5);
                State.Move move = state.getMove(cellNumber);
                if (!Objects.isNull(move)) {
                    state.makeMove(move);
                }
                request.getSession().setAttribute(STATE, state);
                view.put(STATE, state);
                throw new RedirectException("/ticTacToe");
            }
        }
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute(STATE);
        if (Objects.nonNull(state) && state.over() || Objects.isNull(state)) {
            state = new State();
        }
        request.getSession().setAttribute(STATE, state);
        view.put(STATE, state);
        throw new RedirectException("/ticTacToe");
    }

    public static class State {
        public static final int DEFAULT_SIZE = 3;

        private final int size;
        private final String[][] cells;
        private int movesCounter = 0;
        private Result result = Result.RUNNING;

        public int getSize() {
            return size;
        }

        public String[][] getCells() {
            return cells;
        }

        public String getPhase() {
            return result.toString();
        }

        public boolean getCrossesMove() {
            return movesCounter % 2 == 0;
        }

        State(int size) {
            this.size = size;
            this.cells = new String[size][size];
        }

        State() {
            this(DEFAULT_SIZE);
        }

        public boolean over() {
            return result != Result.RUNNING;
        }

        public void makeMove(Move move) {
            if (!over() && move.isValid()) {
                cells[move.getY()][move.getX()] = move.side.toString();
                checkWin(move);
                movesCounter++;
            }
        }

        private Side currentSide() {
            if (movesCounter % 2 == 0) {
                return Side.X;
            }
            return Side.O;
        }

        public static Integer tryParse(String text) {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        public Move getMove(String str) {
            Integer cellY = tryParse(str.substring(0, str.length() / 2));
            Integer cellX = tryParse(str.substring(str.length() / 2));
            if (Objects.isNull(cellX) || Objects.isNull(cellY)) {
                return null;
            }
            return new Move(cellX, cellY, currentSide());
        }

        private void checkWin(State.Move move) {
            final int[] xDirect = new int[]{0, 0, 1, -1, 1, -1, -1, 1};
            final int[] yDirect = new int[]{-1, 1, -1, 1, 0, 0, -1, 1};
            int maxLineCount = 1;
            int lineCount = 1;
            String side = move.side.toString();
            for (int i = 0; i < 8; i++) {
                int x = move.getX() + xDirect[i];
                int y = move.getY() + yDirect[i];
                while (x >= 0 && x < size && y >= 0 && y < size) {
                    if (side.equals(cells[y][x])) {
                        lineCount++;
                        x += xDirect[i];
                        y += yDirect[i];
                    } else {
                        break;
                    }
                }
                if (i % 2 == 1) {
                    maxLineCount = Math.max(maxLineCount, lineCount);
                    lineCount = 1;
                }
            }
            if (maxLineCount == size) {
                result = (side.equals("X") ? Result.WON_X : Result.WON_O);
            } else if (movesCounter == size * size - 1) {
                result = Result.DRAW;
            }
        }

        class Move {
            private final int x;
            private final int y;
            private final Side side;

            Move(int x, int y, Side side) {
                this.x = x;
                this.y = y;
                this.side = side;
            }

            public int getX() {
                return x;
            }

            public int getY() {
                return y;
            }

            public Side getSide() {
                return side;
            }

            public boolean isValid() {
                return (0 <= x && x < size &&
                        0 <= y && y < size &&
                        Objects.isNull(cells[y][x]));
            }
        }

        private enum Result {
            DRAW,
            WON_X,
            WON_O,
            RUNNING,
        }

        private enum Side {
            X, O
        }
    }
}
