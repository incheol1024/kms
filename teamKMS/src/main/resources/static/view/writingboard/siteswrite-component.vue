<template>
    <v-layout wrap>
        <v-flex xs12>
            <v-text-field v-model="curSite.boardDetailDto.subject" label="제목" single-line></v-text-field>
        </v-flex>
        <v-flex xs12>
            <write-component ref="editor" v-bind:read-only="curSite.boardDetailDto.readOnly"></write-component>
        </v-flex>
        <v-flex xs12 lg4>
            <v-btn v-if="!curSite.boardDetailDto.readOnly" :loading="loading" :disabled="loading" color="primary"
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
module.exports =  {
    props: ["siteId","projectId", "boardId"],
    data: () => ({
        buttonName: "New Save",
        loading: false,
        showComment : false,
        curSite: copyObject(SiteDto)
    }),
    mounted() {
        console.log("ㅅㅂ");
        if (this.boardId === "0") {
            this.buttonName = "New Save";
            this.showComment = false;
        } else {
            this.buttonName = "Edit";
            this.showComment = true;
           axios.get(`site/board/${this.siteId}/${this.projectId}/${this.boardId}`).then(value => {
                this.curSite.boardDetailDto = value.data;
                this.$refs.editor.setText(this.curSite.boardDetailDto.contents);
            }).catch(error => catchPromise(error));
            this.curSite.boardId = this.boardId;
        }
    },
  methods: {
      save: function save() {

      }
  }
}
</script>