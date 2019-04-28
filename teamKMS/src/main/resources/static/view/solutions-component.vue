<template>
    <v-layout column>
        {{id}}
        <v-card-title>
            <v-btn slot="activator" color="primary" dark class="mb-2" @click="moveToPage(0)"> 글쓰기</v-btn>
            <v-spacer></v-spacer>
            <v-text-field v-model="search" append-icon="search" label="검색" single-line hide-details></v-text-field>
        </v-card-title>
        <v-data-table :headers="headers" :items="solution" :search="search">
            
            <template slot="headerCell" slot-scope="props">
                <v-tooltip bottom>
                    <span slot="activator">
                    {{ props.header.text }}
                    </span>
                    <span>
                    {{ props.header.text }}
                    </span>
                </v-tooltip>
            </template>
            <template slot="items" slot-scope="props">
                <tr>
                    <td class="text-xs-left" @click="moveToPage(props.item.boardId)">{{ props.item.boardId }}</td>
                    <td class="text-xs-left" @click="moveToPage(props.item.boardId)">{{ props.item.subject }}</td>
                    <td class="text-xs-left" @click="moveToPage(props.item.boardId)">{{ props.item.userId }}</td>
                    <td class="text-xs-left" @click="moveToPage(props.item.boardId)">{{ props.item.hits }}</td>
                    <td class="text-xs-left" @click="moveToPage(props.item.boardId)">{{ props.item.regDate }}</td>
                    <td>
                    <v-btn slot="activator" color="primary" dark class="mb-2" @click="delpage(props.item.boardId)"> 삭제
                    </v-btn>
                    </td>
                </tr>
            </template>
            <v-alert slot="no-results" :value="true" color="error" icon="warning">
                "{{ search }}"에 대한 결과를 찾을 수 없습니다.
            </v-alert>
        </v-data-table>
    </v-layout>
</template>

<script>
    module.exports = {
        name: 'solution',
        props: ['id', 'name'],
        data: () => ({
            search: '',
            headers: [
                {text: '번호', value: 'id'},
                {text: '제목', value: 'title'},
                {text: '작성자', value: 'userName'},
                {text: '조회수', value: 'hits'},
                {text: '등록일자', value: 'regDate'},
                {text: '', value: ''}
            ],
            solution: []
        }),
        created: function () {
            var _this = this;
            this.getSolutionList(_this);
        },

        methods: {
            getSolutionList: function (_this) {
                axios.get("/solution/" + this.id)
                    .then(
                        function (response) {
                            for (var i = 0; i < response.data.length; i++) {
                                _this.solution.push(response.data[i]);
                            }
                        }
                    )
                    .catch(function (error) {
                        console.log(error);
                    })
            },
            moveToPage: function (fuc) {
                this.$router.push("/solutions/write/" + fuc);
            },
            delpage: function (id) {
                if (confirm("삭제하시겠습니까?")) {
                    axios.delete('/solution/' + id)
                        .then(
                            function (response) {
                                console.log(response.data);
                            }
                        )
                        .catch(function (error) {
                            console.log("[ERR] : " + error)
                        })
                }
                this.$router.push("/solutions/" + this.id);
            }
        }
    };
</script>
