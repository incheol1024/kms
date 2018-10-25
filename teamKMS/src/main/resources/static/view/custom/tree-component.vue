<template>
<ul class="tree-list">
	<treenode-component :items="items"></treenode-component>
</ul>
</template>

<script>
module.exports = {
  props: {
    items: {},
    cachekey: ""
  },
  data: () => ({
    cache: {},
    active: {}
  }),
  created() {
    EventBus.$on("nodechange", this.activated);
    this.recurCache(this.items);
  },
  methods: {
    activated: function activated(node) {
      this.active.activeNow = false;
      this.active = node;
    },
    addNode: function addNode(data) {
      this.cache[data[this.cachekey]] = data[this.cachekey];
      this.active.items.children.push(data);
      this.active.showChild = true;
    },
    deleteNode: function deleteNode() {
      console.log("aa")
      delete this.cache[this.active.items[this.cachekey]];
      var index = this.active.parentnode.items.children.indexOf(this.active.items);
      this.active.parentnode.items.children.splice(index, 1);
      this.active = {};
    },
    updateNode: function updateNode(data) {
      this.active.items.name = data;
    },
    moveNode: function moveNode(text) {
      var target = this.cache[text];
      target.children.push(this.active.items);
      var index = this.active.parentnode.items.children.indexOf(this.active.items);
      this.active.parentnode.items.children.splice(index, 1);
    },
    recurCache(data) {
      this.cache[data[this.cachekey]] = data;
      if (data.children && data.children.length) {
        var max = data.children.length;
        for (var i = 0; i < max; i++) {
          this.recurCache(data.children[i]);
        }
      }
    }
  }
};
</script>
  
<style>
.tree-list ul {
  padding-left: 30px;
  margin: 6px 0;
  list-style-type: none;
}

ul {
  list-style-type: none;
}
</style>
