import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import axios from '../../api/axios.jsx';
import FruitItem from './FruitItem.jsx';

const Fruits = () => {
  const family = useSelector((state) => state.family.value);
  const [inventoryFruits, setInventoryFruits] = useState([
    {
      id: 0,
      type: '',
      title: '',
    },
  ]);

  useEffect(() => {
    axios
      .get(`family/${family.id}/inventory/${family.mainSprint.sprintId}`)
      .then((response) => {
        setInventoryFruits(response.data.recordFruitList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  return (
    <div>
      <h2 className="text-2xl text-center">보유 열매</h2>
      <p className="text-center">기록을 통해 열매를 얻을 수 있습니다.</p>
      <div className="flex flex-wrap p-2 pl-6 h-3/4">
        {inventoryFruits.map((fruit, index) => (
          <div
            key={index}
            className="w-10 h-10"
            draggable="true"
            data-fruit-index={index}
          >
            <FruitItem type={fruit.type} title={fruit.title} id={fruit.id} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Fruits;
