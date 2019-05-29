<template>
    <v-layout wrap>
        <v-flex xs12>
            <v-text-field v-model="curSolution.boardDetailDto.subject" label="제목" single-line ></v-text-field>
        </v-flex>
        <v-flex xs12>
            <write-component ref="editor" v-bind:read-only="$route.query.readOnly"></write-component>
        </v-flex>
        <v-flex xs12 lg4>
            <v-btn v-if="!$route.query.readOnly" :loading="loading" :disabled="loading" color="primary" @click="save">
                {{buttonName}}
            </v-btn>
        </v-flex>
        <v-flex v-if="boardId !== 0" xs12>
            <comment-component comment-component :qid="$route.params.id"></comment-component>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ["menuId","id"],
        data: () => ({
            buttonName: "New Save",
            url: "solution",
            boardId: 0,
            loading: false,
            curSolution : copyObject(SolutionDto)
        }),
        watch: {
            id() {
                if (id === "0") {
                    this.buttonName = "New Save";
                    this.url = "solution";
                    this.boardId = 0;
                } else {
                    this.buttonName = "Edit";
                    this.url = "solution";
                    this.boardId = id;
                }
            }
        },
        methods: {
            save: function save() {
                this.loading = true;
                this.curSolution.menuId = this.menuId;
                this.curSolution.boardDetailDto.contents = this.$refs.editor.getText();
                axios.post(this.url, this.curSolution).then(response => {
                    this.$router.push(`/solutions/${this.menuId}`);
                })
                    .catch(error =>
                        catchPromise(error)
                    )
                    .finally(() => {
                        this.loading = false;
                    });

            }
        }
    };

</script>