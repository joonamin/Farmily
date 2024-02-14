import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: {
    id: 0,
    name: '',
    motto: '',
    invitationCode: '',
    profileDto: {
      location: '',
      originalFileName: '',
    },
    tree: {
      id: 0,
      mainRecordFruitDtoList: [
        {
          recordId: 0,
          recordTitle: '',
          position: {
            row: 0,
            col: 0,
          },
        },
      ],
    },
    challengesIds: [0],
    mainSprint: {
      sprintId: 0,
      endDate: '',
    },
    needHarvest: false,
    point: 0,
    fruitSkins: {
      daily: '',
      challenge: '',
      event: '',
    },
  },
};

const familySlice = createSlice({
  name: 'family',
  initialState,
  reducers: {
    setFamily: (state, action) => {
      state.value.id = action.payload.id;
      state.value.name = action.payload.name;
      state.value.invitationCode = action.payload.invitationCode;
      state.value.profileDto = action.payload.profileDto;
      state.value.motto = action.payload.motto;
      state.value.tree = action.payload.tree;
      state.value.challengesIds = action.payload.challengesIds;
      state.value.mainSprint = action.payload.mainSprint;
      state.value.point = action.payload.point;
      state.value.fruitSkins = action.payload.fruitSkins;
    },
    setHarvest: (state, action) => {
      state.value.needHarvest = action.payload.needHarvest;
    },
  },
});

export const { setFamily, setHarvest } = familySlice.actions;

export default familySlice;
