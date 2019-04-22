<template>
    <v-content>
        sol / {{name}} / {{id}} / write page [ {{Solution}} / {{Site}} / {{selected}} ]test / {{fuc}} /

        <v-container id="solution_write" :items="solution_write" grid-list-xl>
            <v-layout row wrap>
                <v-flex xs12 sm6 md3>
                    <v-text-field
                            label="title"
                            placeholder="subject"
                            v-model="subject"
                    ></v-text-field>
                </v-flex>
                <v-flex xs12 sm4>
                    <v-text-field
                            placeholder="solution"
                            label="solution"
                            v-model="solution"
                    ></v-text-field>
                </v-flex>
                <v-flex xs12 sm4>
                    <v-text-field
                            placeholder="site"
                            label="site"
                            v-model="site"
                    ></v-text-field>
                </v-flex>
            </v-layout>
        </v-container>

        <div class="text-xs-center">
            <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor>
        </div>

        <div class="text-xs-center" v-if="id === '0'">
            <v-btn color="success" @click="try_write">등록</v-btn>
        </div>

        <div class="text-xs-center" v-else>
            <v-btn color="success" @click="edit_write">편집</v-btn>
        </div>
    </v-content>
    
</template>

<script>
    module.exports = {
        name: 'solution_write',
        props: ['id', 'name'],
        data: () => ({
            solution: '',
            site: '',
            boardId: '',
            subject:'',
            contents:'',
            editor: ClassicEditor,
            solution_write: []
        }),
        created: function (response) {
            console.log("id = " + this.$route.params.id);
            var _this = this;
            this.getSolution(_this);
        },
        methods: {
            getSolution: function(_this) {
                axios.get("/solution/answer/"+ id)
                .then(
                function(response) {
                    for (var i = 0; i < response.data.length; i++) {
                        _this.solution_write.push(response.data[i]);
                    }
                })
                .catch(function(error) {
                console.log(error);
                })
            },
            try_write: function try_write() {
              let obj_b = {subject:this.subject,contents:this.contents};
              let obj_s = {site:this.site,solution:this.solution};
              let obj = Object.assign({},obj_b,obj_s)
                axios.post('/solution/register', obj)
                    .then(
                        function (response) {
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log("[ERR] : " + error)
                    })
              this.$router.push("/solutions/" + this.id);
            },
            edit_write: function edit_write() {
              let obj_b = {boardId:this.id,subject:this.subject,contents:this.contents};           
              let obj_s = {site:this.site,solution:this.solution};
              let obj = Object.assign({},obj_b,obj_s)
                axios.post('/solution/edit', obj)
                    .then(
                        function (response) {
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                         console.log("[ERR] : " + error)
                    })
              this.$router.push("/solutions/" + this.id);
            },
            cancel_write: function () {
                router.push("/solutions/" + this.id);
            }
        }
    };

</script>