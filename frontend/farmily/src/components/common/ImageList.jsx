import chunsik from '../../assets/images/chunsik.jpg';
import defaultimage from '../../assets/images/defaultimage.png';
import ImageItem from './ImageItem.jsx';

import { Link } from 'react-router-dom';

export default function ImageList({ images }) {
  // console.log(123123123123, images);
  return (
    <div className="h-2/5 p-5 snap-x overflow-x-scroll flex border-b-8">
      {images.length === 0 ? (
        <div className="h-100 mx-auto ">
          <div className="h-full m-1 border-4 border-black rounded-lg flex-shrink-0 snap-center">
            <Link to="/family/record/create/event">
              <img
                className="w-full h-full rounded-lg object-cover"
                src={defaultimage}
                alt="빈 이미지"
              />
            </Link>
          </div>
        </div>
      ) : (
        images.map((item, index) => (
          <ImageItem key={index} image={item.location} id={item.recordId} />
        ))
      )}
    </div>
  );
}
