const UserModel= {"id":"","name":"","type":"USER","groupId":-1,"groupName":"","password":""};
const GroupModel = {"id":0,"name":"Root","parentId":-1,"children":[]};
const BASEPAGERESMODEL ={"list":[""],"total":30};
const BASEPAGEMODEL = {"size": 5, "page": 0, "direction": "ASC", "name": ""};
const BASEPAGEREQMODEL = {"data": {},"pageable": BASEPAGEMODEL};
const ACLMODEL = {"aclId":"","aclName":"","hasPermission":[{"value": 1,"has":false,"name":"CREATE-USER"},{"value":2,"has":false,"name":"CREATE-GROUP"},{"value":3,"has":false,"name":"CREATE-PERMISSION"},{"value":4,"has":false,"name":"CREATE-QNA"},{"value":5,"has":false,"name":"CREATE-SOLUTION"},{"value":6,"has":false,"name":"CREATE-SITE"},{"value":7,"has":false,"name":"DELETE-USER"},{"value":8,"has":false,"name":"DELETE-GROUP"},{"value":9,"has":false,"name":"DELETE-PERMISSION"},{"value":10,"has":false,"name":"DELETE-QNA"},{"value":11,"has":false,"name":"DELETE-SOLUTION"},{"value":12,"has":false,"name":"DELETE-SITE"},{"value":13,"has":false,"name":"MODIFY-USER"},{"value":14,"has":false,"name":"MODIFY-GROUP"},{"value":15,"has":false,"name":"MODIFY-PERMISSION"},{"value":16,"has":false,"name":"MODIFY-QNA"},{"value":17,"has":false,"name":"MODIFY-SOLUTION"},{"value":18,"has":false,"name":"MODIFY-SITE"}]}
const ACEMODEL = {"aclId": "", "aceId": ""};
const PROJECTMODEL = {
    "siteId": 0,
    "projectId": 0,
    "name": "",
    "startDate": "",
    "endDate": "",
    "manager": "",
    "mangerName": ""
};
const SITEMODEL = {"menuId": 0, "siteId": 0, "name": ""};