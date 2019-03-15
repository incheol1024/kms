<template>
    <v-layout column>
        <v-text-field label="Search Word" outline @keyup.enter="searchDoc" v-model="search"></v-text-field>
        <v-card v-for="data in searchData">
            <v-card-title primary-title>
                <div>
                    <div class="headline">Unlimited music now</div>
                    <span>Listen to your favorite artists and albums whenever and wherever, online and offline.</span>
                </div>
            </v-card-title>
            <v-card-actions>
                <v-btn flat>Listen now</v-btn>
            </v-card-actions>
        </v-card>

        <v-card>
            <v-card-title primary-title>
                <div>
                    <div class="headline"><a href="asfasf">TEST1</a></div>
                    <span>위 문서는 우짜구 저짜구</span>
                </div>
            </v-card-title>
            <v-card-actions>
            </v-card-actions>
        </v-card>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            search: "",
            searchData: []
        }),
        methods: {
            searchDoc: function searchDoc() {
                alert("와우!!")
            }
        }
    };
</script>