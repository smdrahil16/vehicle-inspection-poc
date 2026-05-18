output "bucket_name" {
  value = aws_s3_bucket.car_images_bucket.bucket
}

output "bucket_arn" {
  value = aws_s3_bucket.car_images_bucket.arn
}