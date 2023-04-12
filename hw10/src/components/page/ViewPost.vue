<template>
  <div>
    <Post :post="post" :author="users[post.userId].login" :commentsCount="comments.length"/>
    <Comments :post="post" :users="users" :comments="comments" />
    <WriteComment :post="post" :userId="userId"/>
  </div>
</template>

<script>
import Post from "@/components/page/Post.vue";
import Comments from "@/components/page/Comments.vue";
import WriteComment from "@/components/page/WriteComment.vue";

export default {
  name: "ViewPost",
  data: function () {
    return {
      text: "",
      error: "",
    }
  },
  components: {
    WriteComment,
    Comments,
    Post
  },
  props: ["post", "users", "comments", "userId"],
}
</script>

<style scoped>

</style>