Vue.use(Vuetify);
Vue.use(VueRouter);
Vue.use(window.VueCodemirror);
Vue.use(VueCkeditor);
Vue.use(VueCharts);

httpVueLoader.httpRequest = function (url) {
	return axios.get(url)
		.then(function (res) {
			return res.data;
		})
		.catch(function (err) {
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

httpVueLoader.register(Vue, 'view/answerboard/qnaanswer-component.vue');

httpVueLoader.register(Vue, 'view/setting/user-component.vue');
httpVueLoader.register(Vue, 'view/setting/group-component.vue');
httpVueLoader.register(Vue, 'view/setting/permission-component.vue');
httpVueLoader.register(Vue, 'view/custom/tree-component.vue');
httpVueLoader.register(Vue, 'view/custom/treenode-component.vue');
httpVueLoader.register(Vue, 'view/custom/table-component.vue');

Vue.component('tree-component')
Vue.component('treenode-component')
Vue.component('table-component')

EventBus = new Vue();

router = new VueRouter({
	routes: [
		{ path: '/title', component: Vue.component('title-component') },
		{ path: '/sites/:id', component: Vue.component('sites-component'), props: true },
		{ path: '/solutions/:id', component: Vue.component('solutions-component'), props: true },
		{ path: '/qna/:name/:id', component: Vue.component('qna-component'), props: true },
		{ path: '/search', component: Vue.component('search-component') },
		{ path: '/help', component: Vue.component('help-component') },
		{ path: '/sites/write/:id', component: Vue.component('siteswrite-component'), props: true },
		{ path: '/solutions/write/:id', component: Vue.component('solwrite-component'), props: true },
		{ path: '/qna/write/:name/:id', component: Vue.component('qnawrite-component'), props: true },
		{ path: '/qna/answer/:name/:id/:qid', component: Vue.component('qnaanswer-component'), props: true },
		{
			path: '/setting', component: Vue.component('setting-component'),
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
})

var main = new Vue({
	router: router,
	data: () => ({
		drawer: null,
		items: [
			{
				text: 'Solutions',
				children: [],
				type: 'SOL'
			},
			{
				text: 'Sites',
				children: [],
				type: 'SITE'
			},
			{
				text: 'Dev QnA',
				children: [],
				type: 'QNA'
			}
		],
		errormsg : "",
		dialog : false
	}),
	created: function () {
		this.items.forEach(item => {
			if (item.children.length < 1) {
				axios.get("menu/" + item.type)
					.then(function (response) {
						for (var i = 0; i < response.data.length; i++) {
							item.children.push(response.data[i]);
						}						
					}).catch(function (error) {
						openError(error.response.data);
					})
			}
		});
	},
	methods: {
		logout: function logout() {
			axios.post("logout")
				.then(function (response) {
					window.location.replace('login');
				}).catch(function (error) {
					window.location.replace('login');
				})
		},
		getChild: function getChild(item, event) {

		},
		route: function route(children) {
			if (children.type == 'SOL')
				router.push('/solutions/' + children.id);
			else if (children.type == 'SITE')
				router.push('/sites/' + children.id);
			else if (children.type == 'QNA')
				router.push('/qna/' + children.name + "/" + children.id);
		},
		gotitle : function gotitle() {
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
}).$mount('#app')

router.push('title');

/*Vue.config.errorHandler = function (err, vm, info) {
	console.log(err);
}*/

Vue.config.warnHandler = function (err, vm, info) {
	console.log(err);
}

openError = function openError(msg){
	main.errormsg = msg;
	main.dialog = true;
}



//add find polyfill
if (!Array.prototype.find) {
	Object.defineProperty(Array.prototype, 'find', {
		value: function (predicate) {
			// 1. Let O be ? ToObject(this value).
			if (this == null) {
				throw new TypeError('"this" is null or not defined');
			}

			var o = Object(this);

			// 2. Let len be ? ToLength(? Get(O, "length")).
			var len = o.length >>> 0;

			// 3. If IsCallable(predicate) is false, throw a TypeError exception.
			if (typeof predicate !== 'function') {
				throw new TypeError('predicate must be a function');
			}

			// 4. If thisArg was supplied, let T be thisArg; else let T be undefined.
			var thisArg = arguments[1];

			// 5. Let k be 0.
			var k = 0;

			// 6. Repeat, while k < len
			while (k < len) {
				// a. Let Pk be ! ToString(k).
				// b. Let kValue be ? Get(O, Pk).
				// c. Let testResult be ToBoolean(? Call(predicate, T, « kValue, k, O »)).
				// d. If testResult is true, return kValue.
				var kValue = o[k];
				if (predicate.call(thisArg, kValue, k, o)) {
					return kValue;
				}
				// e. Increase k by 1.
				k++;
			}

			// 7. Return undefined.
			return undefined;
		},
		configurable: true,
		writable: true
	});
}
