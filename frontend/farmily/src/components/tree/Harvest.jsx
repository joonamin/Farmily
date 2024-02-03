import { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import mainboard from '../../assets/images/mainboard.png';
import axios from '../../api/axios.jsx';
import { setNeedHarvest } from '../../store/harvest.jsx';
import { setFamily } from '../../store/family.jsx';

export default function Harvest({ title }) {
  const dispatch = useDispatch();

  const family = useSelector((state) => state.family.value);
  const [formData, setFormData] = useState({
    familyId: family.id,
  });

  const onClickHandler = async () => {
    try {
      const harvestRes = await axios.post(`/sprint/${family.sprintId}/harvest`);

      const refreshSprintRes = await axios.post(
        `/family/refreshSprint`,
        formData
      );
      // console.log(refreshSprintRes);

      dispatch(setNeedHarvest(false));

      const familyRes = await axios.get(`/family/${family.id}`);
      console.log(familyRes);

      const familyData = {
        id: familyRes.data.id,
        name: familyRes.data.name,
        motto: familyRes.data.motto,
        tree: familyRes.data.tree,
        challengesIds: familyRes.data.challengesIds,
        sprintId: familyRes.data.sprintId,
      };

      dispatch(setFamily(familyData));
    } catch (err) {
      // 오류 처리
      console.error(err);
    }
  };

  return (
    <>
      <div className="relative hover:cursor-pointer" onClick={onClickHandler}>
        <img
          className="relative mb-16 mr-28 "
          src={mainboard}
          alt="main_board"
        />
        <p className="text-white text-4xl absolute top-20 left-12">{title}</p>
      </div>
    </>
  );
}
