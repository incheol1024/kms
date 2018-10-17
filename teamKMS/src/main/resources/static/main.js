Vue.use(Vuetify);
Vue.use(VueRouter);
Vue.use(window.VueCodemirror);

httpVueLoader.httpRequest = function(url) {
    return axios.get(url)
    .then(function(res) {
        return res.data;
    })
    .catch(function(err) {
    	console.log(err);
        return Promise.reject(err.status);
    });
}

httpVueLoader.register(Vue, 'view/title-component.vue');
httpVueLoader.register(Vue, 'view/sites-component.vue');
httpVueLoader.register(Vue, 'view/solutions-component.vue');
httpVueLoader.register(Vue, 'view/qna-component.vue');
httpVueLoader.register(Vue, 'view/search-component.vue');
httpVueLoader.register(Vue, 'view/help-component.vue');

httpVueLoader.register(Vue, 'view/writingboard/qnawrite-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/siteswrite-component.vue');
httpVueLoader.register(Vue, 'view/writingboard/solwrite-component.vue');
httpVueLoader.register(Vue, 'view/setting-component.vue');

httpVueLoader.register(Vue, 'view/setting/user-component.vue');
httpVueLoader.register(Vue, 'view/setting/group-component.vue');
httpVueLoader.register(Vue, 'view/setting/ETC-component.vue');

const router = new VueRouter({
	 routes: [
		  { path: '/title', component: Vue.component('title-component') },
		  { path: '/sites/:id', component: Vue.component('sites-component') , props: true },
		  { path: '/solutions/:id', component: Vue.component('solutions-component'), props: true },
		  { path: '/qna/:name/:id', component: Vue.component('qna-component'), props: true },
		  { path: '/search', component: Vue.component('search-component')},
		  { path: '/help', component: Vue.component('help-component')},
		  { path: '/sites/write/:id', component: Vue.component('siteswrite-component'), props: true },
		  { path: '/solutions/write/:id', component: Vue.component('solwrite-component'), props: true },
		  { path: '/qna/write/:name/:id', component: Vue.component('qnawrite-component'), props: true },
		  { path: '/setting', component: Vue.component('setting-component'),
			  children: [
			        {
			          path: 'user',
			          component:  Vue.component('user-component')
			        },
			        {
			          path: 'group',
			          component:  Vue.component('group-component')
			        },
			        {
				      path: 'ETC',
				      component:  Vue.component('ETC-component')
				    }
	      ]}
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
            type : 'SOL',
            hide : false
          },
          {
            text: 'Sites',
            children: [],
            type : 'SITE',
            hide : false
          },
          {
              text: 'Dev QnA',
              children: [],
              type : 'QNA',
              hide : false
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
					 item.hide = true;
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
				router.push('/qna/' + children.name + "/" + children.id);
		},
		submit : function submit(){
			router.push('/search');
		},
		setting : function setting(){
			router.push('/setting');
		},
		help : function help(){
			router.push('/help');
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
