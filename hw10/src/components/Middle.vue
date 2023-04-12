<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts" :users="users" :comments="comments"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <ViewPost v-if="page ==='ViewPost'" :post="posts[postId]" :users="users"
                      :comments="commentsByPost" :userId="userId"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import Users from "./page/Users";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "@/components/page/Register.vue";
import ViewPost from "@/components/page/ViewPost.vue";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            postId: null,
        }
    },
    components: {
      ViewPost,
        WritePost,
        Enter,
        Register,
        Index,
        Users,
        Sidebar,
        EditPost
    },
    props: ["posts", "users", "comments", "userId"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        commentsByPost: function () {
            return Object.values(this.comments).filter(comment => comment.postId === this.postId);
        },
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page);
        this.$root.$on("onViewPost", (postId) => {this.postId = postId; this.page = "ViewPost"})
    }
}
</script>

<style scoped>

</style>
