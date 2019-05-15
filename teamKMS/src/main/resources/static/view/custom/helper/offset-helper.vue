<template>
    <div :style="styles" :class="classes" class="v-offset">
        <slot></slot>
    </div>
</template>

<script>
    module.exports = {
        props: {
            fullWidth: {
                type: Boolean,
                default: false
            },
            offset: {
                type: [Number, String],
                default: 0
            }
        },
        computed: {
            classes () {
                return {
                    'v-offset--full-width': this.fullWidth
                }
            },
            styles () {
                return {
                    top: `-${this.offset}px`,
                    marginBottom: `-${this.offset}px`
                }
            }
        }
    }
</script>

<style scoped>
    .v-offset {
        margin: 0 auto;
        max-width: calc(100% - 32px);
        position: relative;
    }
    .v-offset--full-width {
        max-width: 100%;
    }
</style>