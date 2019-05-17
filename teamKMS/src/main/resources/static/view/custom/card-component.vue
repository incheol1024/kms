<template>
    <v-card v-bind="$attrs" v-on="$listeners" :style="styles">
        <offset-helper v-if="hasOffset"
                       :inline="inline"
                       :full-width="fullWidth"
                       :offset="offset">
        <v-card v-if="!$slots.offset" :color="color" :class="`elevation-${elevation}`" class="v-card--material__header" dark>
            <slot v-if="!title && !text" name="header"></slot>
            <span v-else>
                <h4 class="title mb-2" v-text="title"></h4>
                <p class="category" v-text="text"></p>
            </span>
        </v-card>
        <slot v-else name="offset"></slot>

        </offset-helper>
        <v-card-text>
            <slot></slot>
        </v-card-text>

        <v-divider v-if="$slots.actions" class="mx-3"></v-divider>
        <v-card-actions v-if="$slots.actions">
            <slot name="actions"/>
        </v-card-actions>
    </v-card>
</template>

<script>
    module.exports = {
        inheritAttrs: false,
        props: {
            color: {
                type: String,
                default: 'secondary'
            },
            elevation: {
                type: [Number, String],
                default: 10
            },
            inline: {
                type: Boolean,
                default:
                    false
            },
            fullWidth: {
                type: Boolean,
                default: false
            },
            offset: {
                type: [Number, String],
                default: 24
            },
            title: {
                type: String,
                default: undefined
            },
            text: {
                type: String,
                default: undefined
            }
        },
        computed: {
            hasOffset() {
                return this.$slots.header || this.$slots.offset || this.title || this.text
            },
            styles() {
                if (!this.hasOffset) return null;
                return {
                    marginBottom: `${this.offset}px`,
                    marginTop: `${this.offset * 2}px`
                }
            }
        }
    }
</script>

<style scoped>
    v-card--material__header

    .title {
        margin: 0 !important;
        line-height: 1.5em !important;
        letter-spacing: 0 !important;
        font-size: 1.5625rem !important;
    }
    .title small {
        color: #999;
        font-size: 65%;
        line-height: 1;
        font-weight: 400;
    }

    .category {
        font-size: 14px;
    }

    .v-card--material__header.v-card {
        border-radius: 4px;
    }
</style>