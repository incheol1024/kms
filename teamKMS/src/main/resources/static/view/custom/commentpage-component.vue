<template>
    <v-layout row wrap>
        <v-flex xs8>
            <v-card>
                <v-card-text>
                    <v-pagination
                            v-model="page"
                            :dark="true"
                            :length="5"
                            :circle="true"
                            :total-visible="totalVisible"
                            :value="page"
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
                totalVisible: 0,
                maxPage: 5,
                numberOfElements: 0
            }
        },

        created: function () {
            this.getComments(this.page);
        },
        methods: {
            getComments: function (page) {
                var that = this;
                axios.get("/comment/list/" + this.qid,
                    {
                        params: {
                            page: page,
                            size: this.size,
                            sort: this.sort
                        }
                    }
                )
                    .then(
                        function (response) {
                            console.dir(response.data);
                            if (response.data.content.length > 0) {
                                that.removeComment();
                                for (var i = 0; i < response.data.content.length; i++) {
                                    that.comments.push(response.data.content[i]);
                                }
                            }
                            that.setTotalVisible(response);
                            that.setNumberOfElements(response);
                        }
                    )
                    .then(response => {

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
            setTotalVisible: function (response) {

                if (response.data.totalPages < this.maxPage) {
                    console.log(response.data.totalPages);
                    // this.totalVisible = response.data.totalPages;
                    this.totalVisible = 2;
                }
            },
            setNumberOfElements: function (response) {

                const numberOfElements = response.data.numberOfElements;
                console.log("response.data.numberOfElements = " + response.data.numberOfElements)

                if(numberOfElements > 0 ) {
                    this.numberOfElements = numberOfElements;
                }
            }
        },
        watch: {
            page: function (page) {
                this.getComments(page);
            }
        }

    }
</script>
