<template>

    <v-layout row wrap justify-space-around>
        <v-flex xs12>
            <commentwrite-component
                    :id="Number(id)"
                    :name="name"
                    :qid="Number(qid)"
                    :is-required-code-button="isRequiredCodeButton"
                    :is-required-file-button="isRequiredFileButton"
                    @upload-comment="requestFirstPage"
            >
            </commentwrite-component>
        </v-flex>

        <v-flex xs12>
            <commentlist-component
                    ref="commentList"
                    :id="Number(id)"
                    :name="name"
                    :qid="Number(qid)">
            </commentlist-component>
        </v-flex>

    </v-layout>

</template>

<script>
    module.exports = {
        props: {
            id: {
                type: Number,
                required: true
            },
            name: {
                type: String,
                required: true
            },
            qid: {
                type: Number,
                required: true
            },
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
                pageNumber: 1
            }
        },
        methods: {
            requestFirstPage: function () {
                console.log("comment-component requestFirstPage");
                this.$refs.commentList.requestFirstPage();
            }
        },
        watch: {
            pageNumber: function () {
                console.log("comment-component watch pageNumber?" + this.pageNumber);
            }
        },
        computed: {

        }

    }
</script>

