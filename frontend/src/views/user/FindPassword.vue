<template>
  <v-container fill-height style="max-width:450px;">
        <v-layout align-center row wrap>
            <v-flex xs12>
                <v-card>
                    <v-toolbar flat color="white">
                        <v-toolbar-title>비밀번호 찾기</v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-3">
                        <v-text-field
                            v-model="email"
                            label="이메일을 입력하세요"
                            @keyup.enter="findPassword(email)"
                        ></v-text-field>
                        <v-btn 
                          depress
                          large
                          block
                          color="#7C4DFF"
                          dark
                          @click="findPassword(email)"
                        >
                          비밀번호 찾기
                        </v-btn>
                        {{ email }}
                    </div>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data: () => ({
    email: "",
  }),
  methods: {
    ...mapActions(["findPassword"]),
  }
}
</script>