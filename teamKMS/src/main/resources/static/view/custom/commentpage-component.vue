  <template>

         <v-flex xs8>
          <v-card>
            <v-card-text>
              <v-pagination
                v-model="page"
                :length="15"
              ></v-pagination>
            </v-card-text>
          </v-card>
        </v-flex>
  </template>

  <script>
  module.exports = {
    props: [],
    data() {
      return {
      page:1
      }
    },

    created: function() {

    },

    methods: {  
    }
  }
  </script>
