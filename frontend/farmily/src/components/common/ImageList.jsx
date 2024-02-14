import React, { useState, useEffect } from 'react';
import chunsik from '../../assets/images/chunsik.jpg';
import defaultimage from '../../assets/images/defaultimage.png';
import ImageItem from './ImageItem.jsx';
import { Link } from 'react-router-dom';

export default function ImageList({ images }) {
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 이미지 로딩이 완료되면 로딩 상태를 false로 변경
    setLoading(false);
  }, [images]);

  return (
    <div className="h-2/5 p-5 snap-x overflow-x-scroll flex border-b-8">
      {loading ? ( // 로딩 중일 때 로딩 스피너 표시
        <div className="h-100 mx-auto flex items-center justify-center">
          {/* 로딩 스피너를 표시할 부분 */}
          <div
            className="inline-block h-8 w-8 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]"
            role="status"
          >
            <span className="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]">
              Loading...
            </span>
          </div>
        </div>
      ) : images.length === 0 ? (
        <div className="h-100 mx-auto">
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
