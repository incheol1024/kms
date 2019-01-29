const UserModel= {"id":-1,"name":"","type":"USER","groupId":-1,"groupName":"","password":""};
const GroupModel = {"id":-1,"name":"Root","parentid":-1,"children":[]};
const BASEPAGERESMODEL ={"list":[""],"total":30};
const BASEPAGEMODEL = {"size": 5, "page": 0, "direction": "ASC", "name": ""};
const BASEPAGEREQMODEL = {"data": {},"pageable": BASEPAGEMODEL};

const jsTojavaPage = function (pagination) {
    var str = "asc";
    if (pagination.descending) str = "desc";
    return {page: pagination.page, size: pagination.rowsPerPage, sort: pagination.sortBy + "," + str}
};
