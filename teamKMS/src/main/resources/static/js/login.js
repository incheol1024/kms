Vue.use(Vuetify);

new Vue({
	data: () => ({
		name: '',
		password: ''
	}),
	methods: {
		submit() {
			var bodyFormData = new FormData();
			bodyFormData.set('username', this.name);
			bodyFormData.set('password', this.password);
			axios.post('/login', bodyFormData).then(function (response) {
					window.location.replace("/");
				}).catch(function (error) {
					alert("로그인 실패!!! 관리자에게 문의하세요." + error.response.data);
				})
		}
	}
}).$mount('#login')