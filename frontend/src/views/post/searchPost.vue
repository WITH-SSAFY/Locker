<template>
    <div id="container">
        <v-tabs
            id="tabs"
            v-model="tab"
            background-color="transparent"
            color="black"
                    >
            <v-tab
            v-for="item in items"
            :key="item"
            >
            {{item}}
            </v-tab>
        </v-tabs>
        <v-tabs-items v-model="tab">
                <v-tab-item
                v-for="item in items"
                :key="item"
                >
            </v-tab-item>
        </v-tabs-items>
        
        <section
        id="main">
            
            <v-container id="main_container">
                <v-row>
                    <v-col
                        v-for="card in cards"
                        :key="card.title"
                        cols="12"
                        lg="4"
                        md="6"
                      >   

                      <v-card
        >
                            <v-img
                            :src="card.src"
                            class="white--text align-end"
                            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                            height="200px"
                            >
                            <v-card-title v-text="card.title"></v-card-title>
                            </v-img>

                            <v-card-actions>
                                <v-spacer></v-spacer>

                                <v-btn icon>
                                    <v-icon>mdi-heart</v-icon>
                                </v-btn>

                                <v-btn icon>
                                    <v-icon>mdi-bookmark</v-icon>
                                </v-btn>

                                <v-btn icon>
                                    <v-icon>mdi-share-variant</v-icon>
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-col>
                </v-row>
            </v-container>
        </section>
        <aside
        id="side">
            <v-container id="side_container">
            <div>
                사이드 바에유
            </div>
        </v-container>
        </aside>
    </div>
</template>

<script>
//import axios from "../../lib/axios-common.js"; //axios : 서버와 통신하기 위함
  
export default {
    data(){
        return{
            tab : '',
            cards : [
                { title: 'Pre-fab homes', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', flex: 4 },
                { title: 'Favorite road trips', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg', flex:4 },
                { title: 'Best airlines', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', flex: 4 },
                { title: 'Pre-fab homes', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', flex: 4 },
                { title: 'Favorite road trips', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg', flex:4 },
                { title: 'Best airlines', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', flex: 4 },
            ],
            items : ["인기글", "최신글"]
        }
    }
}
</script>

<style scoped>
    #container{
        margin : 25px auto;
        width: 90%;
    }

    #main {
        padding: 0 0 0 0;
        width: 80%;
        float: left;
    }
    #tabs{
        margin-left: 10px;
    }
    
    #main_container{
        margin: 0 0 0 0;
        padding: 0 0 0 0;
    }
    

    #side {
        margin-top: 10px;
        width: 17%;
        float: right;
        border: 1px solid black;
    }

    .v-card{
        margin-left: 10px;
        margin-bottom: 10px;
    }
    
    @media (max-width: 768px){
        #side{
            display: none;
        }
        #main{
            width: 100%;
        }
        .v-card{
            width: 100%;
        }  
    }
    
</style>