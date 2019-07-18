<template>
    <div>

        <v-layout row wrap>
            <v-flex xs12>
                <v-card>
                    <v-card-title>
                        <v-avatar color="grey lighten-4"
                                  :tile="avatarTile"
                                  :size="avatarSize">
                            <img :src="imgSrc"
                                 alt="John">
                        </v-avatar>
                        <v-spacer></v-spacer>
                        <v-btn v-if="true" flat color="orange" @click="pickFile">파일 추가</v-btn>
                        <input
                                type="file"
                                style="display: none"
                                ref="image"
                                accept="image/*"
                                @change="onFilePickedCustom"
                                multiple>
                        <v-btn v-if="isRequiredCodeButton"
                               flat color="orange"
                               @click="viewCodemirror">
                            코드 추가
                        </v-btn>
                        <v-btn
                                :loading="uploading"
                                :disabled="uploading"
                                color="blue-grey"
                                class="white--text"
                                @click.prevent="processAddCommentAndFile">
                            등록
                            <v-icon right dark>cloud_upload</v-icon>
                        </v-btn>
                    </v-card-title>

                    <write-component ref="commentEditor" :toolbar="false"></write-component>

                    <div v-if="codemirror">
                        <v-divider></v-divider>
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
                        <template v-if="fileChips.length > 0" v-for="fileChip in fileChips">
                            <v-chip
                                    v-model="fileChip.deletion"
                                    close
                                    color="green"
                                    outline
                            > {{ fileChip.name }}
                            </v-chip>
                        </template>
                    </v-card-actions>

                </v-card>
            </v-flex>

        </v-layout>

    </div>
</template>

<script>
    module.exports = {
        // props: ['id', 'name', 'qid', 'isRequiredCodeButton'],
        props: {
            id: Number,
            name: String,
            qid: Number,
            isRequiredCodeButton: {
                type: Boolean,
                default: false
            },
            isRequiredFileButton: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                cmtContents: '',
                editorConfig: {},
                cmtCode: '',
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
                fileChips: [],
                fileTransactKey: null,
                fileCount: 0,
                numberOfMaxFile: 2,
                docs: [],
                loader: null,
                uploading: false,
                codemirror: false,
                avatarTile: false,
                avatarSize: '50px',
                imgSrc: 'http://localhost:8089',
                addCommentUrl: "/comment/add",
                addCommentAndFileUrl: "/comment/add/files"
            }
        },
        created: function () {
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
            pickFile: function () {
                this.$refs.image.click();
            },
            onFilePickedCustom: function (e) {

                new Promise((resolve, reject) => {
                    let files = e.target.files;
                    if (files.length <= this.numberOfMaxFile)
                        resolve(files);
                    else
                        reject("number of max files: " + this.numberOfMaxFile);

                })
                    .then((files) => {

                        let filesArray = Array.prototype.slice.call(files);

                        for (var i = 0; i < filesArray.length; i++) {

                            console.dir(filesArray[i]);
                            let file = filesArray[i];
                            let fileReader = new FileReader();
                            fileReader.readAsDataURL(file);


                            fileReader.onload = (fileReader, event) => {
                                this.fileChips.push({
                                    name: file.name,
                                    deletion: true,
                                    file: file
                                });
                            }

                        }
                    }, alert)
                    .catch((error) => console.log(error));
            },
            processAddCommentAndFile: function () {

                new Promise((resolve, reject) => {

                    if (this.assertCommentTextValidation())
                        resolve();
                    else
                        reject("댓글은 공백을 입력할 수 없습니다.");

                })
                    .then(() => {
                        return this.addFile();
                    }, alert) // if files exist trans file, not if next step
                    .then((response) => {
                        console.dir(response.data);
                        this.fileTransactKey = response.data.fileTransactKey;
                        this.fileCount = response.data.fileCount;
                        this.addComment();
                    }) // add comment
                    .then(() => {
                        this.initailizeProperty();
                    })
                    .catch((error) => console.log(error));
            },
            assertCommentTextValidation: function () {
                let textLength = this.$refs.commentEditor.getText().length;
                if (textLength <= 0)
                    return false;
                return true;
            },
            addFile: function () {
                console.log("addFile()")
                // set FormData
                let formData = new FormData();
                formData.append("boardId", this.qid);
                this.fileChips.forEach((fileChip) => {
                    formData.append("multiPartFile", fileChip.file);
                });

                // axios post request
                return axios
                    .post("file/upload/comment",
                        formData,
                        {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        })
                    .then((response) => {
                        return response;
                    })
                    .catch((error) => {
                        console.log(error);
                    })
                    ;
            },
            addComment: function () {

                let url = this.addCommentUrl;
                if (this.fileTransactKey !== undefined && this.fileCount > 0) {
                    url = this.addCommentAndFileUrl;
                }


                console.log(url);

                axios.post(url, {
                    boardId: Number(this.qid),
                    cmtContents: this.$refs.commentEditor.getText(),
                    cmtCode: this.cmtCode,
                    fileTransactKey: this.fileTransactKey,
                    fileCount: this.fileCount
                })
                    .then(response => {
                            this.emitComment(response.data);
                            this.uploading = false;
                        }
                    )
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            initailizeProperty: function () {
                this.fileChips = [];

            },
            viewCodemirror: function () {
                this.codemirror = !this.codemirror;
            }
        },
        computed:
            {
                codemirror() {
                    return this.$refs.myCm.codemirror;
                }
            }
        ,
        mounted() {
            console.log('this is current codemirror object', this.codemirror)
            // you can use this.codemirror to do something...
        }

    }
</script>

    