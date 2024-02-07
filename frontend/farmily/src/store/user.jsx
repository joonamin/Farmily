import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: {
    nickname: '',
    familyInfo: [],
  },
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    getUser: (state, action) => {
      state.value.nickname = action.payload.nickname;
    },
    getFamilies: (state, action) => {
      state.value.familyInfo = action.payload.familyInfo;
    },
  },
});

export const { getUser, getFamilies } = userSlice.actions;

export default userSlice;
