"use strict";

Vue.use(Vuetify);

new Vue({
	data: function data() {
		return {
			name: '',
			password: '',
			loading : false
		};
	},
	methods: {
		submit: function submit() {
			this.loading = true;
			let bodyFormData = new FormData();
			bodyFormData.set('username', this.name);
			bodyFormData.set('password', this.password);
			axios.post('/login', bodyFormData).then(function (response) {
				window.location.replace("/");
			}).catch(function (error) {
				alert("로그인 실패!!! 관리자에게 문의하세요." + JSON.stringify(error.response.data));
			}).finally(() => {this.loading = false;})
		}
	}
}).$mount('#login');