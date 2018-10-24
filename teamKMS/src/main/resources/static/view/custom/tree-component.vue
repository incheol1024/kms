<template>
<ul class="tree-list">
	<treenode-component :items="items"></treenode-component>
</ul>
</template>

<script>
module.exports = {
  props: {
    items: {}
  },
  data: () => ({
    cache: {},
    active: {}
  }),
  created() {
    EventBus.$on("nodechange", this.activated);
  },
  methods: {
    activated: function activated(node) {
      this.active.activeNow = false;
      this.active = node;
    },
    addNode: function addNode(data) {
      this.active.items.children.push(data);
      this.active.showChild = true;
    },
    deleteNode: function deleteNode() {
     
    },
    updateNode: function updateNode(data) {
      this.active.items.name = data;
    },
    moveNode: function moveNode(text, propName) {

    }
  }
};
</script>
  
<style>
.tree-list ul {
  padding-left: 16px;
  margin: 6px 0;
  list-style-type: none;
}

ul {
  list-style-type: none;
}
</style>
