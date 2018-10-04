Vue.use(Vuetify);
Vue.use(VueRouter);
Vue.use(window.VueCodemirror);
Vue.use(VueCkeditor);

httpVueLoader.httpRequest = function(url) {
    return axios.get(url)
    .then(function(res) {
        return res.data;
    })
    .catch(function(err) {
        return Promise.reject(err.status);
    });
}

httpVueLoader.register(Vue, 'view/title-component.vue');
httpVueLoader.register(Vue, 'view/sites-component.vue');
httpVueLoader.register(Vue, 'view/solutions-component.vue');
httpVueLoader.register(Vue, 'view/qna-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/qnawrite-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/siteswrite-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/solwrite-component.vue');

const router = new VueRouter({
	 routes: [
		  { path: '/title', component: Vue.component('title-component') },
		  { path: '/sites/:id', component: Vue.component('sites-component') , props: true },
		  { path: '/solutions/:id', component: Vue.component('solutions-component'), props: true },
		  { path: '/qna/:id', component: Vue.component('qna-component'), props: true },
		  { path: '/sites/write/:id', component: Vue.component('siteswrite-component'), props: true },
		  { path: '/solutions/write/:id', component: Vue.component('solwrite-component'), props: true },
		  { path: '/qna/write/:id', component: Vue.component('qnawrite-component'), props: true }
	]
})

var main = new Vue({
	router : router,
    data: () => ({
    	drawer : null,
        items: [
          {
            text: 'Solutions',
            children: [],
            type : 'SOL'
          },
          {
            text: 'Sites',
            children: [],
            type : 'SITE'
          },
          {
              text: 'Dev QnA',
              children: [],
              type : 'QNA'
          }
        ]
     }),
	 methods: {
		 logout : function logout(){
			 axios.post("logout")
			 .then(function (response) {
				 window.location.replace('login');
			 }).catch(function (error) {
				 window.location.replace('login');
			 })
		},
		getChild : function getChild(item){			
			if(item.children.length < 1){
				 axios.get("getMenuList",{
					 params: { type: item.type }
				 })
				 .then(function (response) {
					 item.children = [];
					 for (var i = 0; i < response.data.length; i++) {
							item.children.push(response.data[i]);
					 }
				 }).catch(function (error) {
					 console.log(error);
				 })				
			}
		},
		route : function route(children) {
			if(children.type == 'SOL')
				router.push('/solutions/' + children.id);
			else if(children.type == 'SITE')
				router.push('/sites/' + children.id);
			else if(children.type == 'QNA')
				router.push('/qna/' + children.id);
		},
		submit : function submit(){
			console.log('검색버튼 눌렸다아');
		},
		setting : function setting(){
			console.log('세팅버튼 눌렸다아');
		},
		help : function help(){
			console.log('헬프 버튼 눌렸다아');
		}
	 }
}).$mount('#app')

router.push('title');

/*Vue.config.errorHandler = function (err, vm, info) {
	console.log(err);
}*/

Vue.config.warnHandler = function (err, vm, info) {
	console.log(err);
}
