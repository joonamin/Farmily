import { createSlice } from '@reduxjs/toolkit';
import axios from '../api/axios.jsx';

const initialState = {
  value: {
    username: '',
    nickname: '',
    accessToken: '',
    // familyId: 0,
  },
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    getAccessToken: (state, action) => {
      state.value.accessToken = action.payload.accessToken;
      axios.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${state.value.accessToken}`;
    },

    setUser: (state, action) => {
      state.value.username = action.payload.username;
      state.value.nickname = action.payload.nickname;
    },
    logOut: (state, action) => {
      state.value.username = '';
      state.value.nickname = '';
      state.value.accessToken = '';
      axios.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${state.value.accessToken}`;
      document.cookie =
        'accessToken' + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
      document.cookie =
        'refreshToken' + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    },
  },
});

export const { setUser, logOut, getAccessToken } = authSlice.actions;

export default authSlice;
