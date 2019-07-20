<template>
    <v-layout row wrap>
        <v-flex xs12>
            <v-dialog
                    v-model="dialog"
                    width="500">
                <commentwrite-component
                        :id="Number(id)"
                        :name="name"
                        :qid="Number(qid)"
                        :is-required-code-button="isRequiredCodeButton"
                        :is-required-file-button="isRequiredFileButton"
                        @upload-comment="requestFirstPage"
                >
                </commentwrite-component>
            </v-dialog>
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
            },
            comment: {
                type: Object,
                required: true
            }
        }
    }
</script>

<style scoped>

</style>