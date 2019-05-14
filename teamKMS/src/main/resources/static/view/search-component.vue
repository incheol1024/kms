<template>
    <v-layout column>
        <v-flex v-for="data in searchData" :key="data.id">
            <v-card >
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
        </v-flex>

        <v-flex>
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
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            searchData: []
        }),
        methods: {
            searchDoc() {
                alert("와우!!");
            }
        }
    };
</script>