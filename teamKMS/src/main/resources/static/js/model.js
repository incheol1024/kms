const UserModel= {"id":"","name":"","type":"USER","groupId":-1,"groupName":"","password":""};
const GroupModel = {"id":0,"name":"Root","parentId":-1,"children":[]};
const BASEPAGERESMODEL ={"list":[""],"total":30};
const BASEPAGEMODEL = {"size": 5, "page": 0, "direction": "ASC", "name": ""};
const BASEPAGEREQMODEL = {"data": {},"pageable": BASEPAGEMODEL};
const ACLMODEL = {"aclId": "", "aclName": "", "hasPermission": ""};
const ACEMODEL = {"aclId": "", "aceId": ""};

const TABLEOPTIONS = {
    header : [],
    search : "",
    page : {
        req : function (page){
            return new Promise(function (resolve, reject) {})
        },
        res : function (value,component) {
            component.total = value.data.totalElements;
            let max = value.data.content.length;
            for (let i = 0; i < max; i++) {
                component.dataset.push(value.data.content[i]);
            }
        }
    },
    deleted : {
        allow : false,
        func : function (item){
            return new Promise(function (resolve, reject) {})
        }
    },
    edited : {
        allow : false,
        func : function (item){
            return new Promise(function (resolve, reject) {})
        }
    },
    select : {
        allow : false,
        selected : []
    }

};
