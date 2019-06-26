<template>

    <v-container grid-list-xs align-content-center>

        <commentwrite-component
                :id="Number(id)"
                :name="name"
                :qid="Number(qid)"
                :is-required-code-button="true"
                :is-required-file-button="true"
                @emitcomment="renderAddComment">
        </commentwrite-component>

        <template v-for="(comment, index) in comments">
            <v-layout row wrap justify-space-around :key="index">
                <v-flex xs12>
                    <v-card>
                        <v-card-title>
                            <v-avatar color="grey lighten-4"
                                      :tile="avatarTile"
                                      :size="avatarSize"
                            >
                                <v-icon dark>account_circle</v-icon>
                            </v-avatar>
                            {{ comment.cmtId }} - {{ comment.cmtDate }}
                            <v-spacer></v-spacer>
                            <v-btn flat icon>
                                <v-icon>build</v-icon>
                            </v-btn>
                            <v-btn flat icon @click="deleteComment(comment.cmtId, index)">
                                <v-icon>delete</v-icon>
                            </v-btn>
                        </v-card-title>

                        <v-divider></v-divider>


                        <v-card-actions>
                            <div v-html="comment.cmtContents"></div>
                        </v-card-actions>

                        <codemirror ref="myCm"
                                    :value="comment.cmtCode"
                                    :options="cmOptions"
                                    v-if="isExistData(comment.cmtCode)"
                        >
                        </codemirror>


                        <v-divider></v-divider>

                        <v-card-actions v-if="isExistData(comment.docId)">
                            <div>
                                <v-chip close color="orange" label outline :key="index"
                                        @click="fileDownload(comment.docId, comment.docName)">
                                    {{comment.docName}}
                                </v-chip>
                            </div>
                        </v-card-actions>
                        <v-card-actions>
                            <v-btn flat icon color="blue lighten-2" @click.prevent="updateLike(comment.cmtId, index)">
                                <v-icon>thumb_up</v-icon>
                            </v-btn>
                            <span class="subheading mr-2">{{comment.cmtLike}}</span>
                        </v-card-actions>
                    </v-card>
                </v-flex>
            </v-layout>
        </template>

        <v-layout row wrap justify-space-around>
            <v-flex xs12>
                <div v-show="seenCommentpage">
                    <commentpage-component
                            :id="id"
                            :name="name"
                            :qid="qid"
                            @setcomments="renderComment"
                            ref="commentPage"
                    ></commentpage-component>
                </div>
            </v-flex>
        </v-layout>

    </v-container>
</template>

<script>
    module.exports = {
        // props: ['id', 'name', 'qid'],
        props: {
            id: Number,
            name: String,
            qid: Number,
            isRequiredCodeButton: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                cmtId: 1,
                editor: ClassicEditor,
                editorDisabled: false,
                editorConfig: {},
                cmtContents: "default comment",
                cmOptions: {
                    tabSize: 4,
                    mode: 'text/javascript',
                    theme: 'monokai',
                    lineNumbers: true,
                    line: true,
                    styleActiveLine: true,
                    lineWrapping: true,
                    lineSeparator: "</br>",
                    readOnly: 'nocursor'
                },
                cmtUserId: "",
                cmtDate: "",
                multiPartFile: [],
                comments: [],
                docs: [],
                seenCommentpage: false,
                avatarTile: true,
                avatarSize: '100px',
                _this: this
            }
        },

        created: function () {
        },

        methods: {
            getDocById: function () {
                var that = this;
                axios.get("/comment/" + this.qid)
                    .then(
                        function (response) {
                            for (var i = 0; i < response.data.length; i++) {
                                console.log(response.data[i]);
                                that.docs.push(response.data[i]);
                            }
                        }
                    )
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            updateComment: function () {
                console.log("updateComment function is called");
                axios.post('/comment/update', {
                    boardId: this.boardId,
                    cmtId: this.cmtId,
                    cmtContents: this.cmtContents
                })
                    .then(
                        function (response) {
                            // router.push("/qna/comment/" + _this.name + "/" + _this.id + "/" + response.data.boardId );

                            _this.comments.push(response.data);

                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            deleteComment: function (cmtId, index) {
                if (confirm('답글을 삭제 하시겠습니까?')) {
                    console.log("deleteComment function is called" + "cmtId = " + cmtId);
                    var _this = this;
                    axios.delete('/comment/delete', {
                        params: {
                            cmtId: cmtId
                        }
                    })
                        .then(
                            function (response) {
                                console.log(response.data);
                                _this.comments.splice(index, 1);
                            }
                        )
                        .catch(function (error) {
                            console.log(error);
                        })
                } else {
                    // Do nothing!
                }
            },
            updateLike: function (cmtId, index) {
                var that = this;
                console.log("updateLike function is called");
                axios.post('/comment/like/' + cmtId)
                    .then(
                        function (response) {
                            that.comments[index].cmtLike = response.data.cmtLike;
                            //that.comments.push(response.data);
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log(error);
                    })
            },
            fileDownload: function (docId, docName) {
                console.log("fileDownload function is called");
                console.log("file download url" + " file/download/" + docId);
                axios.get('/file/download/' + docId,
                    {
                        docId: docId
                    },
                    {
                        headers: {
                            responseType: 'blob'
                        }
                    })
                    .then(
                        function (response) {
                            const url = window.URL.createObjectURL(new Blob([response.data]));
                            const link = document.createElement('a');
                            link.href = url;
                            link.setAttribute('download', docName); //or any other extension
                            document.body.appendChild(link);
                            link.click();
                        }
                    )
                    .catch(function (error) {
                        console.log(error);
                    })
            },
            renderComment: function (comments) {
                this.comments = comments;
                let totalPages = this.$refs.commentPage.getTotalPages();
                if (totalPages > 0)
                    this.seenCommentpage = true;

            },
            renderAddComment: function (data) {
                this.$refs.commentPage.getComments(1);
            },
            isExistData: function (data) {
                if (data === undefined || data === null || data === '0' || 0 === Number(data)) {
                    return false;
                } else {
                    return true;
                }

            },
            onCmReady(cm) {
                console.log('the editor is readied!', cm);
            },
            onCmFocus(cm) {
                console.log('the editor is focus!', cm);
            },
            onCmCodeChange(newCode) {
                console.log('this is new code', newCode);
                this.code = newCode
            },
            onCkViewReady(editor) {
                console.log('this is ckeditor view ready');
            }

        }
    }
</script>

