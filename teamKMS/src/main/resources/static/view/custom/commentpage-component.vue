<template>
    <v-layout row wrap>
        <v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
            <v-card>
                <v-card-text>
                    <v-pagination
                            v-model="number"
                            :dark="true"
                            :length="totalPages"
                            :circle="true"
                            :total-visible="totalVisible"
                            :value="number"
                    ></v-pagination>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ["id", "name", "qid"],
        data() {
            return {
                page: 1,
                size: 3,
                sort: 'cmtId,desc',
                comments: [],
                totalVisible: 5,
                maxPage: 5,
                // page object variable
                empty: false, // 코멘트가 있는지 여부
                first: true, // 첫번째 페이지 여부
                last: false, // 마지막 페이지 여부
                totalPages: 0, // 총 필요 페이지 수
                number: 0, // 현재 페이지 번호 ( -1 한 값임)
                totalElements: 0, // 총 댓글 개수
                numberOfElements: 0 // 현재 댓글 개수
            }
        },

        created: function () {
            console.log("comment page");
            this.getComments(this.number);
        },
        methods: {
            getComments: function (number) {
                var that = this;
                axios.get("/comment/list/" + this.qid,
                    {
                        params: {
                            page: number - 1,
                            size: this.size,
                            sort: this.sort
                        }
                    }
                )
                    .then(
                        function (response) {
                            that.setPageValues(response.data);
                            return response;
                        }
                    )
                    .then(response => {
                        if (response.data.content.length > 0) {
                            that.removeComment();
                            for (var i = 0; i < response.data.content.length; i++) {
                                that.comments.push(response.data.content[i]);
                            }
                        }
                    })
                    .then(response => {
                        that.emitComment();
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            emitComment: function () {
                this.$emit('setcomments', this.comments, '');
            },
            removeComment: function () {
                this.comments = [];
            },
            setPageValues: function (responseData) {
                this.empty = responseData.empty;
                this.first = responseData.first;
                this.last = responseData.last;
                this.totalPages = responseData.totalPages;
                this.number = responseData.number + 1;
                this.totalElements = responseData.totalElements;
                this.numberOfElements = responseData.numberOfElements;
            }
        },
        watch: {
            number: function (number) {
                this.getComments(number);
            }
        }

    }
</script>
