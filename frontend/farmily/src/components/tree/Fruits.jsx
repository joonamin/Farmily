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
        console.log(response.data.recordFruitList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  return (
    <div>
      <h2 className="text-2xl font-bold text-center mb-4">보유 열매</h2>
      <div className="flex flex-wrap p-2 pl-6">
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
