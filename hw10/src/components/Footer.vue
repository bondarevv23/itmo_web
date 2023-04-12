<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov
        <p> users: {{usersCounter}} posts: {{postsCounter}} </p>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["usersCounter", "postsCounter"],
}
</script>

<style scoped>

</style>