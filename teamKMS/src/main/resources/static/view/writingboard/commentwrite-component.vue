<template>
    <div>

        <v-layout row wrap>
            <v-flex xs12>
                <v-card>
                    <v-card-title>
                        <v-avatar color="grey lighten-4">
                        </v-avatar>
                        <v-spacer></v-spacer>
                        <v-btn flat color="orange" @click="viewCodemirror">코드 추가</v-btn>
                    </v-card-title>

                    <write-component ref="editor" :toolbar="false"></write-component>

                    <div v-if="codemirror">
                        <codemirror ref="myCm"
                                    :value="cmtCode"
                                    v-model="cmtCode"
                                    :options="cmOptions"
                                    @ready="onCmReady"
                                    @focus="onCmFocus"
                                    @input="onCmCodeChange">
                        </codemirror>
                    </div>

                    <v-card-actions>
                        <span></span>
                        <v-space></v-space>
                        <v-btn
                                :loading="loading"
                                :disabled="loading"
                                color="blue-grey"
                                class="white--text"
                                @click="loader = 'loading'"
                                @click.prevent="addCommentAndFile">
                            등록
                            <v-icon right dark>cloud_upload</v-icon>
                        </v-btn>
                    </v-card-actions>

                </v-card>
            </v-flex>

            <v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
                <template v-for="doc in docs">
                    <img :src="doc.fileUrl" height="100" v-if="doc.fileName" :key="doc.fileName"/>
                </template>
                <v-text-field label="Select Image" @click='pickFile' v-model='imageName'
                              prepend-icon='attach_file'></v-text-field>
                <input
                        type="file"
                        style="display: none"
                        ref="image"
                        accept="image/*"
                        @change="onFilePickedCustom"
                        multiple
                >
            </v-flex>

        </v-layout>

    </div>
</template>

<script>
    module.exports = {
        props: ['id', 'name', 'qid'],
        data() {
            return {
                editor: ClassicEditor,
                cmtContents: '<p>댓글을 입력하세요~^^..!!</p>',
                editorConfig: {},
                cmtCode: 'const a = 10',
                cmOptions: {
                    tabSize: 4,
                    mode: 'text/javascript',
                    theme: 'monokai',
                    lineNumbers: true,
                    line: true,
                    styleActiveLine: true,
                    lineWrapping: true,
                    lineSeparator: "</br>"
                },
                title: "Image Upload",
                dialog: false,
                imageName: '',
                imageUrl: '',
                imageFile: '',
                fileTransactKey: null,
                fileCount: 0,
                docs: [],
                loader: null,
                loading: false,
                codemirror: false
            }
        },
        created: function () {
            // this.setLangMode();
        },
        watch: {
            mode: function () {
                if (4 === Number(this.id)) {
                    this.cmOptions.mode = 'text/x-java';
                } else if (5 === Number(this.id)) {
                    this.cmOptions.mode = 'text/x-c++src';
                } else if (6 === Number(this.id)) {
                    this.cmOptions.mode = 'text/x-python';
                } else if (7 === Number(this.id)) {
                    this.cmOptions.mode = 'text/x-csharp';
                } else if (8 === Number(this.id)) {
                    this.cmOptions.mode = 'text/html';
                }
            },
            loader() {
                const loader = this.loader;
                this[l] = !this[l];

                setTimeout(() => (this[l] = false), 3000);

                this.loader = null;
            }

        },
        methods: {
            onCmReady(cm) {
                console.log('the editor is readied!', cm)
            },
            onCmFocus(cm) {
                console.log('the editor is focus!', cm)
            },
            onCmCodeChange(newCode) {
                console.log('this is new code', newCode)
                this.code = newCode
            },
            emitComment: function (data) {
                this.$emit('emitcomment', data, '');
            },
            setLangMode: function () {
                console.log("setLangMode is called");
                console.log("this.id =" + this.id);
                console.log(5 === Number(this.id));
                if (4 === Number(this.id)) {
                    this.cmOptions.mode = 'text/x-java';
                } else if (5 === Number(this.id)) {
                    this.cmOptions.mode = 'text/x-c++src';
                } else if (6 === Number(this.id)) {
                    this.mode = 'text/x-python';
                } else if (7 === Number(this.id)) {
                    this.mode = 'text/x-csharp';
                } else if (8 === Number(this.id)) {
                    this.mode = 'text/html';
                }
            },
            handleFileUpload: function () {
                console.log("handleFileUpload is called : ");
                //this.multiPartFile = this.$refs.files.files;

                let uploadedFiles = this.$refs.files.files;
                for (var i = 0; i < uploadedFiles.length; i++) {
                    this.multiPartFile.push(uploadedFiles[i]);
                    console.log("uploadedFiles : " + uploadedFiles[i]);
                    console.log("multiPartFile : " + this.multiPartFile[i]);
                }
                console.log("this.multiPartFile length: " + this.multiPartFile.length);

                for (var i = 0; i < this.multiPartFile.length; i++) {
                    console.log(this.multiPartFile[i]);
                }
            },
            pickFile: function () {
                this.$refs.image.click();
            },
            onFilePicked: function (e) {
                const files = e.target.files
                if (files[0] !== undefined) {
                    this.imageName = files[0].name
                    if (this.imageName.lastIndexOf('.') <= 0) {
                        return
                    }
                    const fr = new FileReader()
                    fr.readAsDataURL(files[0])
                    fr.addEventListener('load', () => {
                        this.imageUrl = fr.result
                        this.imageFile = files[0] // this is an image file that can be sent to server...
                    })
                } else {
                    this.imageName = ''
                    this.imageFile = ''
                    this.imageUrl = ''
                }
            },
            onFilePickedCustom: function (e) {
                const files = e.target.files

                if (files.length > 0) {
                    //const fr = new FileReader();
                    for (var i = 0; i < files.length; i++) {
                        var frArr = [];
                        var fr = new FileReader();

                        fr.readAsDataURL(files[i]);
                        var fileName = files[i].name;
                        var fileUrl = fr.result;

                        var doc = {
                            fileName: fileName,
                            fileUrl: fileUrl,
                            fileData: files[i]
                        };

                        console.dir(doc);
                        this.docs.push(doc);

                    }

                } else {
                    this.docs = [];
                }

            },
            addComment: function () {
                var that = this;
                var url = "/comment/add";
                if (this.fileTransactKey !== undefined && this.fileCount > 0) {
                    url = "/comment/add/files"
                }

                console.log('defined url ' + url);
                axios.post(url, {
                    boardId: Number(this.qid),
                    cmtContents: this.$refs.editor.getText(),
                    cmtCode: this.cmtCode,
                    fileTransactKey: this.fileTransactKey,
                    fileCount: this.fileCount
                })
                    .then(
                        function (response) {
                            console.log(response.data);
                            that.emitComment(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            addCommentAndFile: function () {

                if (this.docs.length < 1) {
                    this.addComment();
                    return;
                }

                var that = this;
                let formData = new FormData();
                formData.append('boardId', this.qid);
                for (var i = 0; i < this.docs.length; i++) {
                    let file = this.docs[i].fileData;
                    formData.append('multiPartFile', file);
                }
                axios.post('/file/upload/comment',
                    formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                )
                    .then(
                        function (response) {
                            that.fileTransactKey = response.data.fileTransactKey;
                            that.fileCount = response.data.fileCount;
                        }
                    )
                    .then(res => {
                        that.addComment();
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            addCommentFile: function (fileTransactKey, fileCount) {
                console.log("addComment function is called and fileTransactKey = " + fileTransactKey + " and fileCount = " + fileCount);
                var that = this;

                axios.post('/comment/add/files/' + this.$route.params.qid, {
                    comFileDto: {
                        commentDto: {
                            cmtContents: this.cmtContents
                        },
                        fileTranscationDto: {
                            fileTransactKey: this.fileTransactKey,
                            fileCount: fileCount
                        }
                    }
                })
                    .then(
                        function (response) {
                            // router.push("/qna/answer/" + _this.name + "/" + _this.id + "/" + response.data.boardId );
                            //_this.answers.push(response.data);
                            console.log(response.data);
                        }
                    )
                    .catch(function (error) {
                        console.log(error);
                    })
            },
            onEditorReady: function (editor) {

            },
            viewCodemirror: function () {
                this.codemirror = !this.codemirror;
            }
        },
        computed: {
            codemirror() {
                return this.$refs.myCm.codemirror
            }
        },
        mounted() {
            console.log('this is current codemirror object', this.codemirror)
            // you can use this.codemirror to do something...
        }

    }
</script>
<style>
    .custom-loader {
        animation: loader 1s infinite;
        display: flex;
    }

    @-moz-keyframes loader {
        from {
            transform: rotate(0);
        }
        to {
            transform: rotate(360deg);
        }
    }

    @-webkit-keyframes loader {
        from {
            transform: rotate(0);
        }
        to {
            transform: rotate(360deg);
        }
    }

    @-o-keyframes loader {
        from {
            transform: rotate(0);
        }
        to {
            transform: rotate(360deg);
        }
    }

    @keyframes loader {
        from {
            transform: rotate(0);
        }
        to {
            transform: rotate(360deg);
        }
    }
</style>
    