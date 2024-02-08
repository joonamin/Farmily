import { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import mainboard from '../../assets/images/mainboard.png';
import axios from '../../api/axios.jsx';
import { setNeedHarvest } from '../../store/harvest.jsx';
import { setFamily, setHarvest } from '../../store/family.jsx';
import HarvestModal from './HarvestModal.jsx';

export default function Harvest({ title }) {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const family = useSelector((state) => state.family.value);
  const [formData, setFormData] = useState({
    familyId: family.id,
  });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [curSprintId, setCurSprintId] = useState(0);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  setCurSprintId(family.mainSprint.sprintId);

  const onClickHandler = async () => {
    try {
      const harvestRes = await axios.post(`/sprint/${curSprintId}/harvest`);

      const refreshSprintRes = await axios.post(
        `/family/refreshSprint`,
        formData
      );
      // console.log(refreshSprintRes);

      dispatch(setHarvest(false));

      const res = await axios.get(`/family/${family.id}`);
      const familyData = {
        id: res.data.id,
        name: res.data.name,
        motto: res.data.motto,
        tree: res.data.tree,
        invitationCode: res.data.invitationCode,
        challengesIds: res.data.challengesIds,
        mainSprint: res.data.mainSprint,
        point: res.data.point,
        fruitSkins: res.data.fruitSkins,
      };

      await dispatch(setFamily(familyData));

      await navigate(`/family/record/${curSprintId}`);
    } catch (err) {
      // 오류 처리
      console.error(err);
    }
  };

  return (
    <>
      <div className="relative hover:cursor-pointer" onClick={openModal}>
        <img
          className="relative mb-24 mr-24 ml-12 "
          src={mainboard}
          alt="main_board"
        />
        <p className="text-white text-4xl absolute top-20 left-16 pl-12">
          {title}
        </p>
      </div>

      <HarvestModal
        isOpen={isModalOpen}
        closeModal={closeModal}
        onClickHandler={onClickHandler}
      />
    </>
  );
}
