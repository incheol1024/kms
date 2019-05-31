<template>
    <v-layout wrap>
        <v-flex xs12>
            <v-text-field v-model="curSolution.boardDetailDto.subject" label="제목" single-line></v-text-field>
        </v-flex>
        <v-flex xs12>
            <write-component ref="editor" v-bind:read-only="curSolution.boardDetailDto.readOnly"></write-component>
        </v-flex>
        <v-flex xs12 lg4>
            <v-btn v-if="!curSolution.boardDetailDto.readOnly" :loading="loading" :disabled="loading" color="primary"
                   @click="save">
                {{buttonName}}
            </v-btn>
        </v-flex>
        <v-flex v-if="showComment" xs12>
            <comment-component comment-component :qid="boardId"></comment-component>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ["menuId", "boardId"],
        data: () => ({
            buttonName: "New Save",
            loading: false,
            showComment : false,
            curSolution: copyObject(SolutionDto)
        }),
        mounted() {
            if (this.boardId === "0") {
                this.buttonName = "New Save";
                this.showComment = false;
            } else {
                this.buttonName = "Edit";
                this.showComment = true;
                axios.get(`solution/${this.menuId}/${this.boardId}`).then(value => {
                    this.curSolution.boardDetailDto = value.data;
                    this.$refs.editor.setText(this.curSolution.boardDetailDto.contents);
                }).catch(error => catchPromise(error))
            }
        },
        methods: {
            save: function save() {
                this.loading = true;
                this.curSolution.menuId = this.menuId;
                this.curSolution.boardDetailDto.contents = this.$refs.editor.getText();
                let fetch = axios.post("solution", this.curSolution);
                if (this.boardId !== "0")
                    fetch = axios.put("solution", this.curSolution);
                fetch.then(response => {
                }).catch(error => catchPromise(error))
                    .finally(() => {
                        this.loading = false;
                        this.$router.push(`/solutions/${this.menuId}`);
                    });
            }
        }
    };

</script>