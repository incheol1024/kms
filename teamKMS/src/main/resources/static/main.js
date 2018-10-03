Vue.use(Vuetify);
Vue.use(VueRouter);

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

const router = new VueRouter({
	 routes: [
		  { path: '/title', component: Vue.component('title-component') },
		  { path: '/sites', component: Vue.component('sites-component') },
		  { path: '/solutions', component: Vue.component('solutions-component') },
		  { path: '/qna', component: Vue.component('qna-component') }
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
				console.log(item);
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
		route : function route() {
			
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
