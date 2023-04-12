<template>
  <div v-if="userId" class="writeComment form-box">
    <div class="header">Write comment</div>
    <div class="body">
      <form @submit.prevent="onWriteComment">
        <div class="field">
          <div class="name">
            <label for="login">Text</label>
          </div>
          <div class="value">
            <input autofocus id="login" name="login" v-model="text"/>
          </div>
        </div>
        <div class="field error">{{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Write">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "WriteComment",
  data: function () {
    return {
      text: "",
      error: "",
    }
  },
  props: ["post", "userId"],
  methods: {
    onWriteComment: function () {
      this.$root.$emit("onWriteComment", this.userId, this.post.id, this.text);
    }
  },
  beforeCreate() {
    this.$root.$on("onWriteCommentValidationError", (error) => this.error = error);
    this.$root.$on("onWriteCommentSuccess", () => { this.error = ""; this.text = ""});
  },
}
</script>

<style scoped>
  .writeComment {
    margin-top: 2rem;
  }
</style>