"use strict";

console.log(window);
Vue.use(Vuetify);
Vue.use(VueRouter);
Vue.use(VueCodemirror);
Vue.use(CKEditor);
Vue.use(VueCharts);
Vue.use(VuetifyUploadButton);

httpVueLoader.httpRequest = function (url) {
    return axios.get(url)
        .then(function (res) {
            return res.data;
        })
        .catch(function (err) {
            return Promise.reject(err.status);
        });
};

httpVueLoader.register(Vue, 'view/title-component.vue');
httpVueLoader.register(Vue, 'view/sites-component.vue');
httpVueLoader.register(Vue, 'view/solutions-component.vue');
httpVueLoader.register(Vue, 'view/qna-component.vue');
httpVueLoader.register(Vue, 'view/search-component.vue');
httpVueLoader.register(Vue, 'view/help-component.vue');
httpVueLoader.register(Vue, 'view/setting-component.vue');

httpVueLoader.register(Vue, 'view/writingboard/qnawrite-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/siteswrite-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/solwrite-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/commentwrite-component.vue');

//리팩토링 대상
httpVueLoader.register(Vue, 'view/board/qnapost-component.vue');

httpVueLoader.register(Vue, 'view/setting/user-component.vue');
httpVueLoader.register(Vue, 'view/setting/group-component.vue');
httpVueLoader.register(Vue, 'view/setting/permission-component.vue');

httpVueLoader.register(Vue, 'view/dashboard/project-component.vue');
httpVueLoader.register(Vue, 'view/dashboard/chart-component.vue');
httpVueLoader.register(Vue, 'view/dashboard/event-component.vue');
httpVueLoader.register(Vue, 'view/dashboard/overview-component.vue');
httpVueLoader.register(Vue, 'view/dashboard/recent-component.vue');

httpVueLoader.register(Vue, 'view/custom/comment-component.vue');
httpVueLoader.register(Vue, 'view/custom/commentpage-component.vue');
httpVueLoader.register(Vue, 'view/custorm/fileupload-component.vue');
httpVueLoader.register(Vue, 'view/custom/tree-component.vue');
httpVueLoader.register(Vue, 'view/custom/treenode-component.vue');
httpVueLoader.register(Vue, 'view/custom/table-component.vue');
httpVueLoader.register(Vue, 'view/custom/question-component.vue');
httpVueLoader.register(Vue, 'view/custom/write-component.vue');
httpVueLoader.register(Vue, 'view/custom/card-component.vue');
httpVueLoader.register(Vue, 'view/custom/shortencard-component.vue');

httpVueLoader.register(Vue, 'view/custom/helper/offset-helper.vue');

Vue.component('comment-component');
Vue.component('commentpage-component');
Vue.component('fileupload-component');
Vue.component('tree-component');
Vue.component('treenode-component');
Vue.component('table-component');
Vue.component('question-component');
Vue.component('write-component');
Vue.component('chart-component');
Vue.component('event-component');
Vue.component('overview-component');
Vue.component('recent-component');
Vue.component('project-component');
Vue.component('card-component');
Vue.component('shortencard-component');
Vue.component('offset-helper');
//for tree
const EventBus = new Vue();

const router = new VueRouter({
    routes: [
        {path: '/title', component: Vue.component('title-component')},
        {path: '/sites/:id', component: Vue.component('sites-component'), props: true},
        {path: '/solutions/:id', component: Vue.component('solutions-component'), props: true},
        {path: '/qna/:name/:id', component: Vue.component('qna-component'), props: true},
        {path: '/search', component: Vue.component('search-component')},
        {path: '/help', component: Vue.component('help-component')},
        {path: '/sites/write/:menuId/:siteId/:projectId/:boardId', component: Vue.component('siteswrite-component'), props: true},
        {path: '/solutions/write/:menuId/:boardId', component: Vue.component('solwrite-component'), props: true},
        {path: '/write/:id', component: Vue.component('write-component'), props: true},
        {path: '/qna/write/:name/:id', component: Vue.component('qnawrite-component'), props: true},
        {path: '/qna/answer/:name/:id/:qid', component: Vue.component('qnapost-component'), props: true},
        {
            path: '/setting',
            component: Vue.component('setting-component'),
            children: [
                {
                    path: 'user',
                    component: Vue.component('user-component')
                },
                {
                    path: 'group',
                    component: Vue.component('group-component')
                },
                {
                    path: 'permission',
                    component: Vue.component('permission-component')
                }
            ]
        }
    ]
});

const main = new Vue({
    router: router,
    data: function data() {
        return {
            drawer: null,
            items: [{
                text: 'Solutions',
                children: [],
                type: 'SOL'
            }, {
                text: 'Sites',
                children: [],
                type: 'SITE'
            }, {
                text: 'Dev QnA',
                children: [],
                type: 'QNA'
            }],
            errormsg: "",
            dialog: false
        };
    },
    created: function created() {
        this.items.forEach(function (item) {
            if (item.children.length < 1) {
                axios.get("menu/" + item.type).then(function (response) {
                    for (let i = 0; i < response.data.length; i++) {
                        item.children.push(response.data[i]);
                    }
                }).catch(err =>
                    catchPromise(err)
                );
            }
        });
    },
    methods: {
        logout: function logout() {
            axios.post("logout").then(function (response) {
                window.location.replace('login');
            }).catch(function (error) {
                window.location.replace('login');
            });
        },
        route: function route(children) {
            if (children.type === 'SOL') router.push('/solutions/' + children.id);
            else if (children.type === 'SITE') router.push('/sites/' + children.id);
            else if (children.type === 'QNA') router.push('/qna/' + children.name + "/" + children.id);
        },
        gotitle: function gotitle() {
            router.push('/title');
        },
        submit: function submit() {
            router.push('/search');
        },
        setting: function setting() {
            router.push('/setting');
        },
        help: function help() {
            router.push('/help');
        }
    }
}).$mount('#app');


router.push('title');

Vue.config.errorHandler = function (err, vm, info) {
    console.log(err);
};

Vue.config.warnHandler = function (err, vm, info) {
    console.log(err);
};

const openError = function openError(msg) {
    main.errormsg = msg;
    main.dialog = true;
};