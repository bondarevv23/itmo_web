<template>
  <div class="datatable">
    <div class="caption">Comments</div>
    <table>
      <thead>
      <tr>
        <th>Login</th>
        <th>Text</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="comment in commentsByPost(post)" :key="comment.id">
        <td>{{ users[comment.userId].login }}</td>
        <td>{{ comment.text }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "Comments",
  props: ["post", "users", "comments"],
  methods: {
    commentsByPost: function (post) {
      return Object.values(this.comments).filter(x => x.postId === post.id).reverse();
    },
  },
}
</script>

<style scoped>

</style>