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
			axios.post('/login', bodyFormData)
				.then(function (response) {
					window.location.replace("/");
				}).catch(function (error) {
					alert("로그인 실패. 이 화면은 바뀔거임");
				})
		}
	}
}).$mount('#login')