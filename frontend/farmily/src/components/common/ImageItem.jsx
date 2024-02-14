import { Link } from 'react-router-dom';

export default function ImageItem({ image, id }) {
  // console.log(image);
  return (
    <Link to={`/family/record/event/${id}`}>
      <div className="size-48 m-1 border-4 border-black rounded-lg flex-shrink-0 snap-center">
        <img
          className="w-full h-full rounded-lg object-contain"
          src={image}
          alt="가족 이미지"
        />
      </div>
    </Link>
  );
}
