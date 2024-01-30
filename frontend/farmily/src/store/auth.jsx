import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: {
    username: 'testuser',
    nickname: 'testnickname',
    accessToken: 'test123123',
    // familyId: 0,
  },
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setUser: (state, action) => {
      state.value.username = action.payload.username;
      state.value.nickname = action.payload.nickname;
      state.value.accessToken = action.payload.accessToken;
    },
  },
});

export const { setUser } = authSlice.actions;

export default authSlice;
