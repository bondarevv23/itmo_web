#!/bin/bash
for ((i=4; i <=100; i++))
  do
  let square=$(($i*$i))
  curl 'http://1d3p.wp.codeforces.com/new' \
  -H 'Cookie: 70a7c28f3de=fhx6rhgwmh72t2nhou; __utmz=71512449.1655928893.2.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utma=71512449.1774215341.1655913129.1656160249.1656194411.17; JSESSIONID=1D1781AE466540BDAF9030EA808B1CBC' \
  --data-raw "_af=34be50b38beccce4&proof=$square&amount=$i&submit=Submit"
  done