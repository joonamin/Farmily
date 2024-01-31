import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: {
    id: 0,
    name: '',
    motto: '',
    tree: {
      id: 0,
      mainRecordFruitDtoList: [
        {
          recordId: 0,
          recordTitle: '',
          row: 0,
          col: 0,
        },
      ],
      mainAccessoryFruitDtoList: [
        {
          id: 0,
          row: 0,
          col: 0,
          accessoryType: 'HIDDEN_FRUIT',
        },
      ],
    },
    challengesIds: [0],
    sprintId: 0,
  },
};

const familySlice = createSlice({
  name: 'family',
  initialState,
  reducers: {
    setFamily: (state, action) => {
      state.value.id = action.payload.id;
      state.value.name = action.payload.name;
      state.value.motto = action.payload.motto;
      state.value.tree = action.payload.tree;
      state.value.challengesIds = action.payload.challengesIds;
      state.value.sprintId = action.payload.sprintId;
    },
  },
});

export const { setFamily } = familySlice.actions;

export default familySlice;
