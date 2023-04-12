<template>
  <div>
    <Post v-for="post in reversePosts" :post="post" :key="post.id"
          :author="users[post.userId].login" :commentsCount="commentsCount(post.id)"/>
  </div>
</template>

<script>
import Post from "@/components/page/Post.vue";

export default {
    name: "Index",
    components: {
      Post
    },
    props: ["posts", "users", "comments"],
    computed: {
      reversePosts: function () {
        return Object.values(this.posts).sort((a, b) => b.id - a.id);
      }
    },
    methods: {
      commentsCount: function (id) {
        return Object.values(this.comments).filter(x => x.postId === id).length;
      },
    },
}
</script>

<style scoped>

</style>
