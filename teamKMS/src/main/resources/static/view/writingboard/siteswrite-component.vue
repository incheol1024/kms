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
    props: ["menuId","siteId","projectId", "boardId"],
    data: () => ({
        buttonName: "New Save",
        loading: false,
        showComment : false,
        curSite: copyObject(SiteDto)
    }),
    mounted() {
         if (this.boardId === "0") {
            this.buttonName = "New Save";
            this.showComment = false;
        } else {
            this.buttonName = "Edit";
            this.showComment = true;
           axios.get(`site/${this.menuId}/${this.siteId}/${this.projectId}/${this.boardId}`).then(value => {
                this.curSite.boardDetailDto = value.data;
                this.$refs.editor.setText(this.curSite.boardDetailDto.contents);
            }).catch(error => catchPromise(error));
            this.curSite.boardId = this.boardId;
        }
    },
  methods: {
      save: function save() {
          this.loading = true;
          this.curSite.siteId = this.siteId;
          this.curSite.projectId=this.projectId;
          this.curSite.boardDetailDto.boardId = this.curSite.boardId;
          this.curSite.boardDetailDto.contents = this.$refs.editor.getText();
          let fetch = axios.post("/site/add", this.curSite);
          if (this.boardId !== "0")
              fetch = axios.put("/site/edit", this.curSite);
          fetch.then(response => {
          }).catch(error => catchPromise(error))
              .finally(() => {
                  this.loading = false;
                  this.$router.push(`/sites/${this.menuId}`);
              });
      }
  }
}
</script>