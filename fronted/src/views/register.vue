<template>
    <v-container fill-height style="max-width:450px;">
        <v-layout align-center row wrap>
            <v-flex xs12>
                <v-alert type="error">
                    비밀번호가 일치하지 않습니다.
                </v-alert>
                <v-card>
                    <v-toolbar flat color="indigo lighten-3">
                        <v-toolbar-title>Registration</v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-3">
                        <v-text-field
                            v-model="nickName"
                            label="닉네임을 입력하세요"
                        ></v-text-field>
                        <v-text-field
                            v-model="email"
                            label="이메일을 입력하세요"
                        ></v-text-field>
                        <v-text-field
                            v-model="password"
                            type="password"
                            label="패스워드를 입력하세요"
                        ></v-text-field>
                        <v-text-field
                            v-model="password"
                            type="password"
                            label="패스워드 확인"
                        ></v-text-field>
                        <v-btn 
                            depress
                            large
                            block
                            color="indigo lighten-5"
                        >
                            가입
                        </v-btn>
                    </div>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>