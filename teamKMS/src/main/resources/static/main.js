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
httpVueLoader.register(Vue, 'view/menu-component.vue');

const router = new VueRouter({
	 routes: [
		  { path: '/title', component: Vue.component('title-component') }
	]
})

var main = new Vue({
	router : router,
	data: () => ({
		drawer : null
	}),
    methods: {
    	
    }
}).$mount('#app')


var OpenError = function OpenError(message){
	if(typeof message.response == "object"){
		main.content = message.response.data;	
	}
	else
		main.content = message.stack;
	main.open();
}

router.push('title');

/*Vue.config.errorHandler = function (err, vm, info) {
	console.log(err);
}*/

Vue.config.warnHandler = function (err, vm, info) {
	console.log(err);
}
