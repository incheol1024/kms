"use strict";

Vue.use(Vuetify);

Vue.prototype.$axios = axios;

new Vue({
	data: function data() {
		return {
			name: '',
			password: ''
		};
	},
	methods: {
		submit: function submit() {
			let bodyFormData = new FormData();
			bodyFormData.set('username', this.name);
			bodyFormData.set('password', this.password);
			this.$axios.post('/login', bodyFormData).then(function (response) {
				window.location.replace("/");
			}).catch(function (error) {
				alert("로그인 실패!!! 관리자에게 문의하세요." + JSON.stringify(error.response.data));
			});
		}
	}
}).$mount('#login');